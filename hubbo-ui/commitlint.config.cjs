module.exports = {
    extends: ['@commitlint/config-conventional'],
    // 校验规则
    rules: {
        'type-enum': [
            2,
            'always',
            [
                // 新特性发布说明
                'feature',
                // bug-fix
                'fix',
                // 文档修改
                'docs',
                // 代码格式调整1
                'style',
                // 重构
                'refactor',
                // 性能提升
                'performance-improve',
                // 测试用例相关
                'test',
                // 其它修改,如修改构建流程,类库,依赖等
                'chore',
                // 版本回滚
                'revert',
                // 编译相关的修改
                'build',
            ],
        ],
        'type-case': [0],
        'type-empty': [0],
        'scope-empty': [0],
        'scope-case': [0],
        'subject-full-stop': [0, 'never'],
        'subject-case': [0, 'never'],
        'header-max-length': [0, 'always', 72],
    },
}