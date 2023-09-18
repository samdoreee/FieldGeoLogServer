package com.samdoree.fieldgeolog.Memo.DTO;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class MemoResponseDTO {

    private Long id;
    private String description;

    public static MemoResponseDTO from(Memo memo) {
        return new MemoResponseDTO(memo);
    }

    private MemoResponseDTO(Memo memo) {
        this.id = memo.getId();
        this.description = memo.getDescription();
    }
}
