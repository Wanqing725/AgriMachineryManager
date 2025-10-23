package org.agrimachinerymanager.common.constant;

/**
 * 系统常量类
 * 定义系统中使用的常量，便于统一管理和维护
 */
public class SystemConstant {

    /**
     * 用户角色常量
     */
    public static class UserRole {
        /** 管理员角色 */
        public static final Integer ADMIN = 1;
        /** 操作员角色 */
        public static final Integer OPERATOR = 2;
    }

    /**
     * 用户状态常量
     */
    public static class UserStatus {
        /** 禁用状态 */
        public static final Integer DISABLED = 0;
        /** 正常状态 */
        public static final Integer ENABLED = 1;
    }

    /**
     * 作业任务状态常量
     */
    public static class TaskStatus {
        /** 待执行 */
        public static final Integer PENDING = 1;
        /** 执行中 */
        public static final Integer IN_PROGRESS = 2;
        /** 已完成 */
        public static final Integer COMPLETED = 3;
        /** 已取消 */
        public static final Integer CANCELLED = 4;
    }

    /**
     * 农机状态常量
     */
    public static class MachineryStatus {
        /** 正常 */
        public static final String NORMAL = "NORMAL";
        /** 维护中 */
        public static final String MAINTAIN = "MAINTAIN";
        /** 故障 */
        public static final String FAULT = "FAULT";
        /** 报废 */
        public static final String SCRAPPED = "SCRAPPED";
    }

    /**
     * 通知已读状态常量
     */
    public static class NotificationReadStatus {
        /** 未读 */
        public static final Integer UNREAD = 0;
        /** 已读 */
        public static final Integer READ = 1;
    }

    /**
     * 数据字典类型常量
     */
    public static class DictType {
        /** 农机类型 */
        public static final String MACHINERY_TYPE = "MACHINERY_TYPE";
        /** 农机状态 */
        public static final String MACHINERY_STATUS = "MACHINERY_STATUS";
        /** 作业类型 */
        public static final String OPERATION_TYPE = "OPERATION_TYPE";
        /** 维护类型 */
        public static final String MAINTAIN_TYPE = "MAINTAIN_TYPE";
        /** 用户角色 */
        public static final String USER_ROLE = "USER_ROLE";
    }

    /**
     * 操作类型常量（用于操作日志）
     */
    public static class OperateType {
        /** 新增 */
        public static final String ADD = "ADD";
        /** 更新 */
        public static final String UPDATE = "UPDATE";
        /** 删除 */
        public static final String DELETE = "DELETE";
        /** 查询 */
        public static final String QUERY = "QUERY";
        /** 登录 */
        public static final String LOGIN = "LOGIN";
        /** 登出 */
        public static final String LOGOUT = "LOGOUT";
    }

    /**
     * HTTP状态码常量
     */
    public static class HttpStatus {
        /** 成功 */
        public static final int OK = 200;
        /** 请求参数错误 */
        public static final int BAD_REQUEST = 400;
        /** 未授权 */
        public static final int UNAUTHORIZED = 401;
        /** 拒绝访问 */
        public static final int FORBIDDEN = 403;
        /** 资源不存在 */
        public static final int NOT_FOUND = 404;
        /** 服务器内部错误 */
        public static final int INTERNAL_SERVER_ERROR = 500;
    }

    /**
     * Spring Security角色名称常量
     */
    public static class SecurityRole {
        /** 管理员角色名 */
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        /** 操作员角色名 */
        public static final String ROLE_OPERATOR = "ROLE_OPERATOR";
        /** 普通用户角色名 */
        public static final String ROLE_USER = "ROLE_USER";
    }

    /**
     * Token相关常量
     */
    public static class Token {
        /** Token前缀 */
        public static final String TOKEN_PREFIX = "Bearer ";
        /** Authorization请求头名称 */
        public static final String AUTHORIZATION_HEADER = "Authorization";
    }
}

