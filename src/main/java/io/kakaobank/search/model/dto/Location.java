package io.kakaobank.search.model.dto;

import static io.kakaobank.search.utility.StringUtility.removeWhiteSpace;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Location {
    @Schema(name = "name", description = "장소 이름", example = "덕수궁")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Location)) {
            return false;
        }

        Location anotherLocation = (Location) o;

        return removeWhiteSpace(name).equals(removeWhiteSpace((anotherLocation.name)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
