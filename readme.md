# Build Spring Boot + MongoDB + Angular Application

## Step 1: Build Java 8 + Spring Boot + MongoDB server-side Rest API
### In IntelliJ IDEA Ultimate Edition
#### File --> New --> Project --> Spring Boot Initializer
    * group: com.example
    * artifact: application-name 
    * Java version 1.8
    * description: application-description
    
    * Web: web
    * NoSql: Mongo
    
    ===> Finish
    
#### create packages and classes:
##### Create package *model*
    * Create class "Fact" with properties id, text, and timestamp.
    * Add constructor with text and timestamp.
    * Add Getters and Setters.
    * Override method "toString()".
    * Create class "ChuckNorrisFactFact" with properties according to JSON requirements.
    NOTE: Can use Lombok and then use annotations @NoArgsConstructor, @Getter, @Setter, @EqualsAndHashCode, @ToString and not need to actually write code for them. 
    
##### Create package *repository*
    * Create intercafe "FactRepository" extending MongoDB Repository.
    * Give "FactRepository" annotation of @Repository.
    * Add more functions to "FactRepository" as needed.
    
##### Create package *service*
    * Create class "FactService".
    * Give "FactService"" the @Service annotation.
    * Add private instace "factRepository" of class "FactRepository".
    * Add method "List<Fact> getAll()" that will return "factRepository.findAll()".
    * Add method "Fact create(String text, LocalDate createdOn)"/
    * Add code to "service.create(String, LocalDate)".
    
##### Create package *controller*
    * Create class "FactController".
    * Give "FactController" the @RestController annotation.
    * Add private instance "factService" of class "FactService"
    * Add method "List<Fact> getAll()" that will return "factrService.getAll()".
    * Add Annotation @RequestMapping("/fact/getAll") to method .getAll().
    * Add method "Fact create(@RequestParam String text)".
    * Add annotation @RequestMapping("/fact/create") to controller.create(..).
    * Add code to "service.create(String)".
     
#### Update *application.properties* file to connect to **MongoDB:
    #spring.data.mongodb.uri=mongodb://localhost:27017/database_name
    spring.data.mongodb.uri=mongodb://username:password@host:port/database_name

## Step 2: Build Angular client
    * go to the projects main folder
      e.g. D:/JavaProjects/IdeaProjects/chuck_norris_random_facts_in_yodish
    * Write command-line and run NG command to create the client:
      ng install client
    * Goto "client" folder that was created
      cd client
    * Write command to creat some more Angular material:
      npm install --save @angular/material @angular/cdk
      
## Step 3: Create Angular client page with 2 tabs according to requirements.
    * Call GET request to receive Chuck Norris random fact.
    * When receiving random fact, call GET request to translate random fact to Yodish.
    
