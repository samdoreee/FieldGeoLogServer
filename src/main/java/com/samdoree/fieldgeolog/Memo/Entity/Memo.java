package com.samdoree.fieldgeolog.Memo.Entity;

import com.samdoree.fieldgeolog.Memo.DTO.MemoRequestDTO;
import com.samdoree.fieldgeolog.File.Entity.File;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> fileList = new ArrayList<>();

    public static Memo createFrom(Spot spot, MemoRequestDTO memoRequestDTO) {
        return Memo.builder()
                .spot(spot)
                .memoRequestDTO(memoRequestDTO)
                .build();
    }

    @Builder
    private Memo(Spot spot, MemoRequestDTO memoRequestDTO) {
        this.description = memoRequestDTO.getDescription();
        this.spot = spot;
    }

    public void modifyMemo(MemoRequestDTO memoRequestDTO) {
        this.description = memoRequestDTO.getDescription();
    }

    //== 연관관계 메서드 ==//
    public void addFile(File file) {
        fileList.add(file);
        file.belongToMemo(this);
    }

    public void removeFile() {
        fileList.clear();
    }
}