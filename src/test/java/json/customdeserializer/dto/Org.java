package json.customdeserializer.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import json.customdeserializer.extend.OrgCustomDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = OrgCustomDeserializer.class)
public class Org {

    private String oid;
    private String nameFull;
    private String nameShort;
}
