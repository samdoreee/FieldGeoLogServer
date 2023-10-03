package com.samdoree.fieldgeolog.Thumbnail.Service;

import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.Picture.Entity.Picture;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import com.samdoree.fieldgeolog.Thumbnail.Entity.Thumbnail;
import com.samdoree.fieldgeolog.Thumbnail.Repository.ThumbnailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThumbnailRegisterService {

    private final ThumbnailRepository thumbnailRepository;

    @Transactional
    public Thumbnail addPersonalRecordThumbnail(PersonalRecord personalRecord, Picture picture) throws Exception {

        Thumbnail thumbnail = Thumbnail.createFrom(personalRecord, null, picture);
        return thumbnailRepository.save(thumbnail);
    }

    @Transactional
    public Thumbnail addSpotThumbnail(Spot spot, Picture picture) throws Exception {

        Thumbnail thumbnail = Thumbnail.createFrom(null, spot, picture);
        return thumbnailRepository.save(thumbnail);
    }
}