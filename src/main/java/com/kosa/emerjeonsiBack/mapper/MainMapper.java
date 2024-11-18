package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MainMapper {
    List<Exhibition> getAllExhibitions();

    Exhibition getExhibitionById(Long exhibitionNo);

    List<Exhibition> getExhibitionsByName();

    List<Exhibition> searchByFilter(@Param("selectedFilter") String selectedFilter, @Param("searchInput") String searchInput);

}