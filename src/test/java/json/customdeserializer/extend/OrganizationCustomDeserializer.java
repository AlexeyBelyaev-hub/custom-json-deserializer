package json.customdeserializer.extend;

import json.customdeserializer.KeyValueArrayDeserializer;
import json.customdeserializer.dto.OrgOrganization;

public class OrganizationCustomDeserializer extends KeyValueArrayDeserializer<OrgOrganization> {
    public OrganizationCustomDeserializer(){
        super(OrgOrganization.class,"column", "value");
    }
}
