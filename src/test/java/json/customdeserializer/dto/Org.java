package json.customdeserializer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import json.customdeserializer.extend.OrgCustomDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = OrgCustomDeserializer.class)
public class Org {

    private String oid;
    private String nameFull;
    private String nameShort;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy")
    private LocalDate createDate;
}
