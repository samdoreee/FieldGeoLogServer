package com.samdoree.fieldgeolog.Memo.DTO;

import com.samdoree.fieldgeolog.File.DTO.FileResponseDTO;
import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoResponseDTO {

    private Long id;
    private String description;
    private List<FileResponseDTO> fileList = new ArrayList<>();

    public static MemoResponseDTO from(Memo memo) {
        return new MemoResponseDTO(memo);
    }

    private MemoResponseDTO(Memo memo) {
        this.description = memo.getDescription();
        if (memo.getFileList() != null && !memo.getFileList().isEmpty()) {
            this.fileList = memo.getFileList().stream().map(FileResponseDTO::new).collect(Collectors.toList());
        }
    }
}
