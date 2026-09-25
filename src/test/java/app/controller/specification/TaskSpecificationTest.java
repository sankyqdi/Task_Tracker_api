package app.controller.specification;

import app.model.Task;
import app.specification.TaskSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class TaskSpecificationTest {

    private TaskSpecification taskSpecification;

    @BeforeEach
    void setUp() {
        taskSpecification = new TaskSpecification();
    }

    @Test
    @DisplayName("build с пустыми параметрами не фильтрует запрос (cb.conjunction)")
    void build_WithNullParams_ReturnsConjunction() {
        Specification<Task> spec = taskSpecification.build(null);
        assertThat(spec).isNotNull();
    }

    @Test
    @DisplayName("nameContains возвращает предикат поиска по названию без учета регистра")
    void nameContains_CreatesLikePredicate() {
        Root<Task> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        when(cb.lower(any())).thenReturn(mock(jakarta.persistence.criteria.Path.class));

        Specification<Task> spec = taskSpecification.nameContains("test");
        spec.toPredicate(root, query, cb);

        verify(cb, times(1)).like(any(), eq("%test%"));
    }

    @Test
    @DisplayName("levelContains возвращает предикат равенства уровня важности")
    void levelContains_CreatesEqualPredicate() {
        Root<Task> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Specification<Task> spec = taskSpecification.levelContains((byte) 5);
        spec.toPredicate(root, query, cb);

        verify(cb, times(1)).equal(any(), eq((byte) 5));
    }
}
