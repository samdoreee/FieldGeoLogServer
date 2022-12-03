package com.samdoree.fieldgeolog.Memo.Service;

import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Memo.Repository.MemoRepository;
import com.samdoree.fieldgeolog.Spot.Repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoSearchService {

    private final SpotRepository spotRepository;
    private final MemoRepository memoRepository;

    public List<MemoResponseDTO> getAllMemoList(Long spotId) {

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());

        List<Memo> memoList = memoRepository.findAllBySpotId(spotId);
        return memoList.stream().map(MemoResponseDTO::from).collect(Collectors.toList());
    }

    public MemoResponseDTO getOneMemo(Long spotId, Long memoId) {

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new NullPointerException());

        Memo memo = memoRepository.findById(memoId).get();
        return MemoResponseDTO.from(memo);
    }
}
