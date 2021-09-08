package json.customdeserializer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import json.customdeserializer.dto.Org;
import json.customdeserializer.dto.OrgOrganization;
import json.customdeserializer.extend.OrganizationCustomDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class KeyValueArrayDeserializerTest {

    @Test
    void testDeserializeExtraFields() throws IOException {
        //given
        String json =
                "[\n" +
                        "            {\n" +
                        "                \"column\": \"oid\",\n" +
                        "                \"value\": \"1\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"column\": \"nameFull\",\n" +
                        "                \"value\": \"Company 1\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"column\": \"nameShort\",\n" +
                        "                \"value\": \"C1\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"column\": \"oldOid\",\n" +
                        "                \"value\": \"2\"\n" +
                        "            }," +
                        " {\n" +
                        "                \"column\": \"createDate\",\n" +
                        "                \"value\": \"16.10.2016\"\n" +
                        "            }" +
                        "        ]";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Org.class, new KeyValueArrayDeserializer<>(Org.class,"column","value"));
        mapper.registerModule(module);
        //when
        Org org = mapper.readValue(json, Org.class);
        //then
        assertEquals("1", org.getOid());
        assertEquals(
                "Company 1",
                org.getNameFull());
        assertEquals("C1", org.getNameShort());
        assertEquals(LocalDate.of(2016,10,16),org.getCreateDate());
    }

    @Test
    void testDeserializeNotEnoughFields() throws IOException {
        //given
        String json =
                "[\n" +
                        "            {\n" +
                        "                \"column\": \"oid\",\n" +
                        "                \"value\": \"1\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"column\": \"nameFull\",\n" +
                        "                \"value\": \"Company 1\"\n" +
                        "            }\n" +
                        "        ]";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);
        //when
        Org org = mapper.readValue(json, Org.class);
        //then
        assertEquals("1", org.getOid());
        assertEquals(
                "Company 1",
                org.getNameFull());
        Assertions.assertNull(org.getNameShort());
    }

    @Test
    void testDeserializeNullFields() throws IOException {
        //given
        String json =
                "[\n" +
                        "            {\n" +
                        "                \"column\": \"oid\",\n" +
                        "                \"value\": \"1\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"column\": \"nameFull\",\n" +
                        "                 \"value\": null\n"+
                        "            }\n" +
                        "        ]";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);
        //when
        Org org = mapper.readValue(json, Org.class);
        //then
        assertEquals("1", org.getOid());
        assertNull(org.getNameFull());
        assertNull(org.getNameShort());
    }


    @Test
    void testDeserializeOrganization() throws IOException {
        //given
        String json =
                "[\n" +
                        "            {\n" +
                        "                \"column\": \"oid\",\n" +
                        "                \"value\": \"1\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"column\": \"nameFull\",\n" +
                        "                \"value\": \"Company 1\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"column\": \"nameShort\",\n" +
                        "                \"value\": \"C1\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"column\": \"oldOid\",\n" +
                        "                \"value\": \"2\"\n" +
                        "            }" +
                        "        ]";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OrgOrganization.class,new OrganizationCustomDeserializer());
        mapper.registerModule(module);
        //when
        OrgOrganization org = mapper.readValue(json, OrgOrganization.class);
        //then
        assertEquals("1", org.getOid());
        assertEquals(
                "Company 1",
                org.getNameFull());
        assertEquals("C1", org.getNameShort());
        assertEquals("2", org.getOldOid());
    }
}