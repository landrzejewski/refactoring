package pl.training.refactoring.patterns.fn;

public class FunctionalStyleBuilder {
    static class User {
        String name;
        int age;
        String email;

        @Override
        public String toString() {
            return name + " (" + age + "), " + email;
        }
    }

    interface UserBuilder {
        UserBuilder name(String name);
        UserBuilder age(int age);
        UserBuilder email(String email);
        User build();
    }

    public static void main(String[] args) {
        UserBuilder builder = createUserBuilder();

        User user = builder
            .name("Alice")
            .age(30)
            .email("alice@example.com")
            .build();

        System.out.println(user);
    }

    static UserBuilder createUserBuilder() {
        User u = new User();
        return new UserBuilder() {
            public UserBuilder name(String name) { u.name = name; return this; }
            public UserBuilder age(int age) { u.age = age; return this; }
            public UserBuilder email(String email) { u.email = email; return this; }
            public User build() { return u; }
        };
    }
}
