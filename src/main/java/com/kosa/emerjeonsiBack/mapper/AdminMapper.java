package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.dto.ExhibitionData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    static void saveAll(List<Exhibition> exhibitions) {
    }

    List<ExhibitionData> getAllExhibitions();

    ExhibitionData getExhibitionById(int exhibitionDataNo);  // 중복 체크용 메서드 추가

    List<ExhibitionData> getExhibitionDataList();
}