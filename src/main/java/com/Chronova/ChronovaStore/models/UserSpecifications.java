package com.Chronova.ChronovaStore.models;
import com.Chronova.ChronovaStore.models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> withAdvancedFilters(String username, String email) {
        return Specification.where(usernameContains(username))
                .and(emailContains(email));
    }

    private static Specification<User> usernameContains(String username) {
        return (root, query, cb) -> {
            if (username == null || username.isEmpty()) return null;
            return cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
        };
    }

    private static Specification<User> emailContains(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isEmpty()) return null;
            return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }
}
