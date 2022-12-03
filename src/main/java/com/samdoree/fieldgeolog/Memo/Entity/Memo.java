package com.samdoree.fieldgeolog.Memo.Entity;

import com.samdoree.fieldgeolog.Memo.DTO.MemoRequestDTO;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @Column(columnDefinition = "TEXT")
    private String description;

    public static Memo createFrom(Spot spot, MemoRequestDTO memoRequestDTO) {
        return Memo.builder()
                .spot(spot)
                .memoRequestDTO(memoRequestDTO)
                .build();
    }

    @Builder
    private Memo(Spot spot, MemoRequestDTO memoRequestDTO) {
        this.description = memoRequestDTO.getDescription();
        changeSpot(spot);
    }

    public void modifyMemo(MemoRequestDTO memoRequestDTO) {
        this.description = memoRequestDTO.getDescription();
    }

    //== 연관관계 편의 메소드 ==/
    public void changeSpot(Spot spot) {

        // 기존의 spot과 memo의 관계를 끊는다
        if (this.spot != null) {
            this.spot.getMemoList().remove(this);
        }

        this.spot = spot;   // memo -> spot
        if (spot != null)
            spot.getMemoList().add(this);   // spot -> memo
    }
}