package by.koval.importApp.service;

import by.koval.importApp.dto.ReqParams;
import by.koval.importApp.model.OldNote;
import by.koval.importApp.repository.NoteRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<OldNote> getAll(ReqParams reqParams) {
        Specification<OldNote> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("clientGuid")), "%" + reqParams.getClientGuid() + "%"));
            predicates.add(criteriaBuilder.between(root.get("modifiedDateTime"),
                    reqParams.getDateTo(),
                    reqParams.getDateFrom()));
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
        return noteRepository.findAll(specification);
    }
}
