package cleanmanage;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CleanmanageRepository extends PagingAndSortingRepository<Cleanmanage, Long>{

	Optional<Cleanmanage> findBy고객휴대폰번호( String get고객휴대폰번호);


}