import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CharacterModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Pdf;

import java.io.InputStream;
import java.util.List;

public class JsonTest {
    private static final ClassLoader cl = Pdf.class.getClassLoader();

    @Test
    @DisplayName("Поиск содержимого в файле .json")
    public void jsonParsingTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("StarWarsPersons.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CharacterModel> characterModel = objectMapper.readValue(stream, new TypeReference<>() {
            });

            Assertions.assertThat(characterModel).hasSize(3);

            CharacterModel firstJedi = characterModel.get(0);
            Assertions.assertThat(firstJedi.getId()).isEqualTo(1);
            Assertions.assertThat(firstJedi.getFirstName()).isEqualTo("Anakin");
            Assertions.assertThat(firstJedi.getLastName()).isEqualTo("Skywalker");
            Assertions.assertThat(firstJedi.getLightSaberColor()).isEqualTo("Blue");
            Assertions.assertThat(firstJedi.getForce()).isEqualTo(5);

            CharacterModel secondJedi = characterModel.get(1);
            Assertions.assertThat(secondJedi.getId()).isEqualTo(2);
            Assertions.assertThat(secondJedi.getFirstName()).isEqualTo("Obi-Wan");
            Assertions.assertThat(secondJedi.getLastName()).isEqualTo("Kenobi");
            Assertions.assertThat(secondJedi.getLightSaberColor()).isEqualTo("Blue");
            Assertions.assertThat(secondJedi.getForce()).isEqualTo(3);

            CharacterModel thirdJedi = characterModel.get(2);
            Assertions.assertThat(thirdJedi.getId()).isEqualTo(3);
            Assertions.assertThat(thirdJedi.getFirstName()).isEqualTo("Yoda");
            Assertions.assertThat(thirdJedi.getLastName()).isEqualTo("");
            Assertions.assertThat(thirdJedi.getLightSaberColor()).isEqualTo("Green");
            Assertions.assertThat(thirdJedi.getForce()).isEqualTo(4);
        }
    }
}
