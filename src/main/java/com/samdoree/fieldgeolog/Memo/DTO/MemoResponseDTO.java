package com.samdoree.fieldgeolog.Memo.DTO;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoResponseDTO {

    private String description;

    public static MemoResponseDTO from(Memo memo) {
        return new MemoResponseDTO(memo);
    }

    private MemoResponseDTO(Memo memo) {
        this.description = memo.getDescription();
    }
}
