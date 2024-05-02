package org.flab.hyunsb.framework.persistence.repository;

import java.util.List;
import org.flab.hyunsb.framework.persistence.entity.region.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {


    @Query(value = """
        select r.id
        from region r, 
            (select tr.lat as tlat, tr.lng as tlng 
                from region tr 
                where tr.id = :regionId) t 
        order by st_distance(point(r.lat, r.lng), point(t.tlat, t.tlng)) 
        limit 5
        """)
    List<Long> findNearFiveByRegionIdFrom(@Param("regionId") Long regionId);
}
