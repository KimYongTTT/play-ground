package io.kakaobank.location.model.dto.response;

import static io.kakaobank.location.utility.StringUtility.removeWhiteSpace;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Location {
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
