package util;

import java.util.Objects;

public class Pair<S, T> {
    S left;
    T right;

    public Pair(S left, T right) {
        this.left = left;
        this.right = right;
    }

    public S getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }

    public void setLeft(S left) {
        this.left = left;
    }

    public void setRight(T right) {
        this.right = right;
    }

    public static <S, T> Pair<S, T> of(S left, T right) {
        return new Pair<>(left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) &&
                Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
