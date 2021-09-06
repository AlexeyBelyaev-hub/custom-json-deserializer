package json.customdeserializer.extend;

import json.customdeserializer.KeyValueArrayDeserializer;
import json.customdeserializer.dto.Org;

public class OrgCustomDeserializer extends KeyValueArrayDeserializer<Org> {
    public OrgCustomDeserializer() {
        super(Org.class,"column","value");
    }
}
