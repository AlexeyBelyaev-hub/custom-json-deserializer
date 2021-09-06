# Custom json deserializer

**Goal of the project is to collect usefull implementations of custom deserializers**

- **KeyValueArrayDeserializer.**<br> 
  Parse json to java object in case fields represents array of key - values nodes.
  <br>For example:
  ```       
      [
           {
             "column": "oid"
             "value": "1"
            },
            {
              "column": "nameFull",
              "value": "Company 1"
             },
            {
              "column": "nameShort",
              "value": "C1" 
            },
            {
              "column": "oldOid",
              "value": "2"
            }
       ]
    ```
  This is going to be parsed to java object:
  ```
  @Data
  public class Org {
      
      private String oid;
      private String nameFull;
      private String nameShort;
  
  }
  ```
  
  You can use it as KeyValueArrayDeserializer like this:

  ```
   ObjectMapper mapper = new ObjectMapper();
   SimpleModule module = new SimpleModule();
   
   module.addDeserializer(Org.class, new KeyValueArrayDeserializer<>(Org.class,"column","value"));      
   
   mapper.registerModule(module);   
   Org org = mapper.readValue(json, Org.class);
  ```

  Or extend it with no args constructor implementation to use it with Jackson annotations 
  
  ```
  @Data
  @JsonDeserialize(using = OrgCustomDeserializer.class)
  public class Org {      
      private String oid;
      private String nameFull;
      private String nameShort;
  }
  
  public class OrgCustomDeserializer extends KeyValueArrayDeserializer<Org> {
    public OrgCustomDeserializer() {
        super(Org.class,"column","value");
    }  
  }
