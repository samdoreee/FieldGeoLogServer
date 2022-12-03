package com.samdoree.fieldgeolog.Memo.Service;

import com.samdoree.fieldgeolog.Memo.DTO.MemoRequestDTO;
import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoRegisterService {

    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public MemoResponseDTO addMemo(Long spotId, MemoRequestDTO memoRequestDTO) throws Exception {

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());

        Memo memo = Memo.createFrom(spot, memoRequestDTO);
        return MemoResponseDTO.from(memoRepository.save(memo));
    }
}
