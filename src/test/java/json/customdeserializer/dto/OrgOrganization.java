package json.customdeserializer.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import json.customdeserializer.extend.OrganizationCustomDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = OrganizationCustomDeserializer.class)
public class OrgOrganization {

    private static String COLUMNS = "columns=oid&columns=nameFull&columns=nameShort";
    private String oid;
    private String oldOid;
    private String nameFull;
    private String nameShort;


}
