package fixture;

public class BugPattern {
    // ES_COMPARING_STRINGS_WITH_EQ / RC_REF_COMPARISON style bug
    public boolean isAdmin(String role) {
        String admin = new String("ADMIN");
        return role == admin;
    }

    // NP_NULL_ON_SOME_PATH style bug
    public int length(String s) {
        String value = s.trim().isEmpty() ? null : s;
        return value.length();
    }
}
