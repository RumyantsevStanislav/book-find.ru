package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import server.entities.Cover;
import server.entities.dtos.CoverDtoImpl;
import server.utils.PropertiesLoader;

import java.util.Properties;

@Mapper
@Component
public interface CoverMapper {

    CoverMapper COVER_MAPPER = Mappers.getMapper(CoverMapper.class);
    Properties conf = PropertiesLoader.loadYamlPropertiesBySpring();
    int MAX_IMAGE_IN_FOLDER = 10000;

    @Mapping(source = "path", target = "path", qualifiedByName = "mapCoverPath")
    CoverDtoImpl toDto(Cover cover);

    @Named("mapCoverPath")
    static String mapCoverPath(String path) {
        String host = conf.getProperty("app.path.img");
        return host +
                Integer.parseInt(path) / MAX_IMAGE_IN_FOLDER +
                "/" +
                path +
                "/" +
                "cover";
    }
}
