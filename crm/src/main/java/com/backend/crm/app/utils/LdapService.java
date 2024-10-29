package com.backend.crm.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.util.List;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    public List<String> searchUsers(String searchTerm) {
        String base = "ou=users,dc=sg,dc=local";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        // Прямое использование searchTerm без экранирования
        return ldapTemplate.search(base, "(uid=" + searchTerm + ")", searchControls, (Attributes attributes) -> {
            return attributes.get("cn") != null ? attributes.get("cn").get().toString() : "Unknown";
        });
    }

    public List<String> getAllEmployees() {
        String base = "ou=Users,dc=SG,dc=local";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        // Используем более общий фильтр для поиска всех пользователей
        return ldapTemplate.search(base, "(objectClass=*)", searchControls, (Attributes attributes) -> {
            // Здесь вы можете выбрать любое поле, которое вам нужно
            // Например, выводить все доступные атрибуты:
            StringBuilder userInfo = new StringBuilder();
            try {
                userInfo.append("CN: ").append(attributes.get("cn") != null ? attributes.get("cn").get().toString() : "Unknown").append(", ");
                userInfo.append("DN: ").append(attributes.get("dn") != null ? attributes.get("dn").get().toString() : "Unknown");
            } catch (Exception e) {
                userInfo.append("Ошибка при получении атрибутов: ").append(e.getMessage());
            }
            return userInfo.toString();
        });
    }

    public void printAllEmployees() {
        System.out.println("Запуск метода printAllEmployees");
        List<String> employees = getAllEmployees();
        System.out.println("Список сотрудников (найдено " + employees.size() + "):");
        for (String employee : employees) {
            System.out.println(employee);
        }
    }

}
