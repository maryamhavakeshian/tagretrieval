package com.github.tagRetrieval.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Property {

    private String Type;
    private static final Map<String, String> shortTypes = Collections.unmodifiableMap(new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("ArrayAccessExpr", "ArAc");
            put("ArrayBracketPair", "ArBr");
            put("ArrayCreationExpr", "ArCr");
            put("ArrayCreationLevel", "ArCrLvl");
            put("ArrayInitializerExpr", "ArIn");
            put("ArrayType", "ArTy");
            put("AssertStmt", "Asrt");
            put("AssignExpr:and", "AsAn");
            put("AssignExpr:assign", "As");
            put("AssignExpr:lShift", "AsLS");
            put("AssignExpr:minus", "AsMi");
            put("AssignExpr:or", "AsOr");
            put("AssignExpr:plus", "AsP");
            put("AssignExpr:rem", "AsRe");
            put("AssignExpr:rSignedShift", "AsRSS");
            put("AssignExpr:rUnsignedShift", "AsRUS");
            put("AssignExpr:slash", "AsSl");
            put("AssignExpr:star", "AsSt");
            put("AssignExpr:xor", "AsX");
            put("BinaryExpr:and", "And");
            put("BinaryExpr:binAnd", "BinAnd");
            put("BinaryExpr:binOr", "BinOr");
            put("BinaryExpr:divide", "Div");
            put("BinaryExpr:equals", "Eq");
            put("BinaryExpr:greater", "Gt");
            put("BinaryExpr:greaterEquals", "Geq");
            put("BinaryExpr:less", "Ls");
            put("BinaryExpr:lessEquals", "Leq");
            put("BinaryExpr:lShift", "LS");
            put("BinaryExpr:minus", "Minus");
            put("BinaryExpr:notEquals", "Neq");
            put("BinaryExpr:or", "Or");
            put("BinaryExpr:plus", "Plus");
            put("BinaryExpr:remainder", "Mod");
            put("BinaryExpr:rSignedShift", "RSS");
            put("BinaryExpr:rUnsignedShift", "RUS");
            put("BinaryExpr:times", "Mul");
            put("BinaryExpr:xor", "Xor");
            put("BlockStmt", "Bk");
            put("BooleanLiteralExpr", "BoolEx");
            put("CastExpr", "Cast");
            put("CatchClause", "Catch");
            put("CharLiteralExpr", "CharEx");
            put("ClassExpr", "ClsEx");
            put("ClassOrInterfaceDeclaration", "ClsD");
            put("ClassOrInterfaceType", "Cls");
            put("ConditionalExpr", "Cond");
            put("ConstructorDeclaration", "Ctor");
            put("DoStmt", "Do");
            put("DoubleLiteralExpr", "Dbl");
            put("EmptyMemberDeclaration", "Emp");
            put("EnclosedExpr", "Enc");
            put("ExplicitConstructorInvocationStmt", "ExpCtor");
            put("ExpressionStmt", "Ex");
            put("FieldAccessExpr", "Fld");
            put("FieldDeclaration", "FldDec");
            put("ForeachStmt", "Foreach");
            put("ForStmt", "For");
            put("IfStmt", "If");
            put("InitializerDeclaration", "Init");
            put("InstanceOfExpr", "InstanceOf");
            put("IntegerLiteralExpr", "IntEx");
            put("IntegerLiteralMinValueExpr", "IntMinEx");
            put("LabeledStmt", "Labeled");
            put("LambdaExpr", "Lambda");
            put("LongLiteralExpr", "LongEx");
            put("MarkerAnnotationExpr", "MarkerExpr");
            put("MemberValuePair", "Mvp");
            put("MethodCallExpr", "Cal");
            put("MethodDeclaration", "Mth");
            put("MethodReferenceExpr", "MethRef");
            put("NameExpr", "Nm");
            put("NormalAnnotationExpr", "NormEx");
            put("NullLiteralExpr", "Null");
            put("ObjectCreationExpr", "ObjEx");
            put("Parameter", "Prm");
            put("PrimitiveType", "Prim");
            put("QualifiedNameExpr", "Qua");
            put("ReturnStmt", "Ret");
            put("SingleMemberAnnotationExpr", "SMEx");
            put("StringLiteralExpr", "StrEx");
            put("SuperExpr", "SupEx");
            put("SwitchEntryStmt", "SwiEnt");
            put("SwitchStmt", "Switch");
            put("SynchronizedStmt", "Sync");
            put("ThisExpr", "This");
            put("ThrowStmt", "Thro");
            put("TryStmt", "Try");
            put("TypeDeclarationStmt", "TypeDec");
            put("TypeExpr", "Type");
            put("TypeParameter", "TypePar");
            put("UnaryExpr:inverse", "Inverse");
            put("UnaryExpr:negative", "Neg");
            put("UnaryExpr:not", "Not");
            put("UnaryExpr:posDecrement", "PosDec");
            put("UnaryExpr:posIncrement", "PosInc");
            put("UnaryExpr:positive", "Pos");
            put("UnaryExpr:preDecrement", "PreDec");
            put("UnaryExpr:preIncrement", "PreInc");
            put("UnionType", "Unio");
            put("VariableDeclarationExpr", "VDE");
            put("VariableDeclarator", "VD");
            put("VariableDeclaratorId", "VDID");
            put("VoidType", "Void");
            put("WhileStmt", "While");
            put("WildcardType", "Wild");
            put("WildcardType", "Wild");

        }
    });
    public String getType(boolean shorten) {
        if (shorten) {
            return shortTypes.getOrDefault(Type, Type);
        } else {
            return Type;
        }
    }

}
