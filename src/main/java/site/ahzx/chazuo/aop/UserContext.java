package site.ahzx.chazuo.aop;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    // 使用ThreadLocal存储当前用户
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    // 设置当前用户
    public void setCurrentUser(String username) {
        currentUser.set(username);
    }

    // 获取当前用户
    public String getCurrentUser() {
        return currentUser.get();
    }

    // 清理
    public void clear() {
        currentUser.remove();
    }
}