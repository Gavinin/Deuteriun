package com.deuteriun.system.mbg;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;


public class ModifyCommentGenerator implements CommentGenerator {

    private static final String Author = "Gavinin";

    private Properties properties;

    private boolean suppressDate;

    private boolean suppressAllComments;

    private boolean addRemarkComments;

    private SimpleDateFormat dateFormat;

    public ModifyCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        addRemarkComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {


        if (compilationUnit instanceof Interface) {
            Interface interfaze = (Interface) compilationUnit;
            interfaze.addJavaDocLine("/**");
            interfaze.addJavaDocLine(" *");
            interfaze.addJavaDocLine(" * @author " + Author);
            interfaze.addJavaDocLine(" * @date " + getDateString());
            interfaze.addJavaDocLine(" */");
        }
    }


    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {

        if (innerClass instanceof TopLevelClass) {
            TopLevelClass topLevelClass = (TopLevelClass) innerClass;
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Builder"));
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.NoArgsConstructor"));
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.AllArgsConstructor"));
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@Builder");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addAnnotation("@AllArgsConstructor");
        }
    }

    /**
     * 实体类的注释
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }

        // 实体类注释
        // BaseRecordGenerator类没有调用addClassComment()方法，调用了addModelClassComment()，所以在这里添加实体类的注释
        topLevelClass.addJavaDocLine("/**");
        // 这里写死了注释内容，没法获取到表的注释（？？？），如商品信息表
        // 可以找到表名，如product_item
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author " + Author);
        topLevelClass.addJavaDocLine(" * @date " + getDateString());
        topLevelClass.addJavaDocLine(" */");

        // 添加实体类的注解
        // 因为MBG没有给普通实体类生成注解，所以这里手动调用一下
        // BaseRecordGenerator类没有调用addClassAnnotation()
        addClassAnnotation(topLevelClass, introspectedTable, null);
    }

    /**
     * 实体类字段注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    /**
     * 通用方法注释
     *
     * <p>如Dao中的方法
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    /**
     * getter方法的注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }

    /**
     * setter方法的注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
                                           Set<FullyQualifiedJavaType> imports) {
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
                                           IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
                                   Set<FullyQualifiedJavaType> imports) {
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
                                   IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
    }


    @Override
    public void addFileComment(KotlinFile kotlinFile) {
    }

    @Override
    public void addComment(XmlElement xmlElement) {
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
    }

    /**
     * 返回一个格式化日期
     *
     * @return
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }
}
