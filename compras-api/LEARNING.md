# Learning Report Day 1 - ComprasAPI
I am creating this .md file in order to recap all the troubleshooting's and new things that I possibly learn during this first day.
Writing helps me review and I feel I learn more when I write down what I went through to reach the goal.
## 1. Dependencies used.
```
<dependencies>
    <!--- Quarkus dependencies reactive --->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
    </dependency>
     <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-resteasy-reactive</artifactId>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-mutiny</artifactId>
    </dependency>
    <!--- Persistence with MongoDB ft Panache --->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-mongodb-panache</artifactId>
    </dependency>
   <!--- MapStruct for DTO --->
    <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct</artifactId>
      <version>${org.mapstruct.version}</version>
    </dependency>
    <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct-processor</artifactId>
      <version>${org.mapstruct.version}</version>
      <scope>provided</scope>
    </dependency>
    <!--- Lombok --->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>${org.projectlombok.version}</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-arc</artifactId>
    </dependency>
    <!--- For unit testing --->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-junit5</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <scope>test</scope>
    </dependency>
 </dependencies>
```

## 2. Lombok and MapStruct config to working together.
I found this change curious, because last year (2022) it wasn't necessary.

From [MapStruct page](https://mapstruct.org/faq/#Can-I-use-MapStruct-together-with-Project-Lombok):
_Essentially, MapStruct will wait until Lombok has done all its amendments before generating mapper classes for Lombok-enhanced beans.
If you are using Lombok 1.18.16 or newer you also need to add lombok-mapstruct-binding in order to make Lombok and MapStruct work together._

So, you need to add is this in the _pom.xml_ (additional to the dependencies) inside <configuration/> tags.
```
<configuration>
    ...
    <annotationProcessorPaths>
        <path>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok</artifactId>
          <version>${org.projectlombok.version}</version>
        </path>
        <path>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok-mapstruct-binding</artifactId>
          <version>0.2.0</version>
        </path>
        <path>
          <groupId>org.mapstruct</groupId>
          <artifactId>mapstruct-processor</artifactId>
          <version>${org.mapstruct.version}</version>
        </path>
   </annotationProcessorPaths>
</configuration>
```

## 2. Mapper configuration

### No more cdi, use jakarta.
This was pretty difficult to figure it out, but hopefully I get an [answer](https://github.com/quarkusio/quarkus/issues/32983.), _if you can consider it as one_.

The situation was when I'm trying to run the microservice I got this error message:

```
Couldn't find type javax.enterprise.context.ApplicationScoped. Are you missing a dependency on your classpath?
```

The solution? In the _SolicitudMapper.java_, instead of "cdi",

```
@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
```

Change it for "jakarta".

```
@Mapper(componentModel = "jakarta", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
```

In the _answer_ that I found it didn't give a clear explanation. I deduce it has to be with "javax" be renamed to "jakarta" and for some
_bug_ reason when you tried to launch the microservice it looks for the old name. Doing a lil research I found that 'cdi' component model
on a @Mapper resulted in an @ApplicationScoped implementation class, which couldn't get properly resolved in my case, so that is the reason
of the launched error. Plus, since I am using java 17 and Quarkus 3.0.x the re-naming cause turns more evident.

In the [documentation of MapStruct](https://mapstruct.org/documentation/stable/reference/html/#configuration-options) you will find more about @Mapper and its componentModel param.
If I found a better explanation, I will share it.

### Mapping changes the solicitudId and timestamp each time I did a GET/PUT.
This was a little exasperating because I know the issue was with the mapper but don't know how to tell it to not update the solicitudId if they already got one.
So, to solve that I have to set up two things:
1. In the method that translates from DTO to entity add
```
@Mapping(target = "solicitudId", ignore = true)
@Mapping(target = "timestamp", ignore = true)
```
I add also timestamp because it was affected as well. These annotations avoid that each time the mapper is called to change that attributes from
solicitud.

2. Create the next method with these annotation: 
```
@AfterMapping
default void mapId(SolicitudDTO solicitudDTO, @MappingTarget Solicitud solicitud) {
   if (solicitudDTO.getSolicitudId() != null) {
   solicitud.setSolicitudId(solicitudDTO.getSolicitudId());
   }
 }
```
Suggested by chatGPT after asking the same question several times. **_@AfterMapping_** marks the method as an @AfterMapping method, 
which means it will be executed after the mapping process is finished. In simple words, it allows you to customize the mapping behavior 
by performing additional logic or transformations on the target object (solicitud) based on the source object (solicitudDTO). 
In this case, it ensures that the solicitudId value is preserved during the mapping process if it exists in the source object.

## 3. Repository implementation.
Well, I kinda forget how to proceed when I want a reactive approach, especially using Panache. But I did my best effort and made possible the integration with Multiny.

If you want to read more about Multiny, here are some links:

- [Quarkus reactive](https://quarkus.io/guides/getting-started-reactive) 
- [Multiny in Quarkus](https://quarkus.io/guides/mutiny-primer)
- [Multiny documentation](https://smallrye.io/smallrye-mutiny/2.2.0/)
- [Flow control and Back-pressure](https://quarkus.io/blog/mutiny-back-pressure/)

In order to use the types Uni and Multi, I create a Repository and do its implementation in a Service interface.
The methods that will give as an answer multiple values will be Multi and the single-answer-ones will be Uni.

I used to do a Uni<List<Solicitud>> to handle multiple values but, instead I want to try Multi<Solicitud> 
after reading [this](https://stackoverflow.com/questions/74425038/is-there-a-difference-between-unilistt-vs-multit-in-quarkus-resteasy-react).
So, it comes pretty nice when you are programming the finds, add and delete. The thing turns curious with modify().

As I understood reading some foros, MongoDB looks for keep the "unique" register with "_id", so, when I try to use persistOrUpdate() I'm still having the old document
before the change and the new one because diff ObjectIds are generated each time I do a register. To handle this, the only idea that works for now is to delete the 
old solicitud and save the edited one. That's why if you check my modify() method in ServiceRepository, the solution is purely functional to
archive that:

```
Uni.createFrom()
    .item(solicitud)
    .onItem()
    .transformToUni(solicitud1 -> remove(solicitud1.getSolicitudId()))
    .onItem()
    .transformToUni(aBoolean -> add(solicitud));
```

The reason why I have to use transformToUni() instead of transform() is because _transform()_ is used to apply a transformation to each element in the 
stream, while _transferToUni()_ is used to convert the stream into a Uni object for further asynchronous processing. Both methods are useful 
for handling reactive operations in Reactive Panache. I made the analogy with map() and flapMap() usages and concepts.

Aside of that, everything was simple to set on the endpoints.

## 4. Date Formatting
In order to archive a format for Tarjeta's fechaCaducidad field, I implement a pair of serializers.
The format that I wanted is 'MM-yy'. You can check the implementation in the _utils/serialization_ folder.
Once you got that, you must and the next annotations over the fechaCaducidad field

```
@JsonDeserialize(using = LocalDateDeserializer.class)
@JsonSerialize(using = LocalDateSerializer.class)
```

## 5. Id configuration
For MongoDb it's good to have your own id and not depend on the ObjectId. My suggestion is to set
with a different name (not id) and autogenerate it. I did mine String with UUID trimmed up to 10 characters.

###
