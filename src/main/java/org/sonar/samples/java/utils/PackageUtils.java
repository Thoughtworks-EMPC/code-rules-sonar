package org.sonar.samples.java.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import javax.annotation.Nullable;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.PackageDeclarationTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

public class PackageUtils {
    private PackageUtils() {
    }

    public static String packageName(@Nullable PackageDeclarationTree packageDeclarationTree, String separator) {
        if (packageDeclarationTree == null) {
            return "";
        } else {
            Deque<String> pieces = new LinkedList();

            ExpressionTree expr;
            MemberSelectExpressionTree mse;
            for(expr = packageDeclarationTree.packageName(); expr.is(new Kind[]{Kind.MEMBER_SELECT}); expr = mse.expression()) {
                mse = (MemberSelectExpressionTree)expr;
                pieces.push(mse.identifier().name());
                pieces.push(separator);
            }

            if (expr.is(new Kind[]{Kind.IDENTIFIER})) {
                IdentifierTree idt = (IdentifierTree)expr;
                pieces.push(idt.name());
            }

            StringBuilder sb = new StringBuilder();
            Iterator var5 = pieces.iterator();

            while(var5.hasNext()) {
                String piece = (String)var5.next();
                sb.append(piece);
            }

            return sb.toString();
        }
    }
}
