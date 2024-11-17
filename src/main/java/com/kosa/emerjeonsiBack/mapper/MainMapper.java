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

    Object searchByFilter(String selectedFilter, String searchInput);
}