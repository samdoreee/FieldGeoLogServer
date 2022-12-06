package com.samdoree.fieldgeolog.Memo.Service;

import com.samdoree.fieldgeolog.File.Entity.File;
import com.samdoree.fieldgeolog.Memo.DTO.MemoRequestDTO;
import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoModifyService {

    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;

    @Value("${fieldgeolog.fileDir}")
    private String fileDir;

    @Transactional
    public MemoResponseDTO modifyMemo(Long spotId, Long memoId, MemoRequestDTO memoRequestDTO, MultipartFile[] multipartFiles) {

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new NullPointerException());

        memo.modifyMemo(memoRequestDTO);

        if (multipartFiles != null) {
            memo.removeFile();
            for (MultipartFile multipartFile : multipartFiles) {
                memo.addFile(File.createFile(multipartFile, fileDir, spotId, memoId));
            }
        }

        return MemoResponseDTO.from(memo);
    }

}
