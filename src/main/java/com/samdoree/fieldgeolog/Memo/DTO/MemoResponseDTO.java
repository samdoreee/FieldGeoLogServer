package com.samdoree.fieldgeolog.Memo.DTO;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Picture.DTO.PictureResponseDTO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class MemoResponseDTO {

    private Long id;
    private String description;
    private List<PictureResponseDTO> pictureResponseDTOList;

    public static MemoResponseDTO from(Memo memo) {
        return new MemoResponseDTO(memo);
    }

    public MemoResponseDTO(Memo memo) {
        this.id = memo.getId();
        this.description = memo.getDescription();
        this.pictureResponseDTOList = memo.getPictureList().stream().map(PictureResponseDTO::new).collect(Collectors.toList());
    }
}
