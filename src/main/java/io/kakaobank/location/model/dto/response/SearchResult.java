package io.kakaobank.location.model.dto.response;

import static io.kakaobank.location.utility.StringUtility.removeWhiteSpace;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@AllArgsConstructor
@ToString
public class SearchResult {
    private String locationName;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof SearchResult)) {
            return false;
        }

        SearchResult anotherResult = (SearchResult) o;

        return removeWhiteSpace(locationName).equals(removeWhiteSpace((anotherResult.locationName)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName);
    }
}
