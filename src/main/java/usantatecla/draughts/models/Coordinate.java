package usantatecla.draughts.models;

import usantatecla.draughts.utils.ConcreteCoordinate;

import java.util.ArrayList;
import java.util.List;

public class Coordinate extends ConcreteCoordinate {

    private static final int LOWER_LIMIT = 0;
    private static final int UPPER_LIMIT = 7;
    private static final int DIMENSION = UPPER_LIMIT + 1;

    public Coordinate(int row, int column) {
        super(row,column);
    }

    public static Coordinate getInstance(String format) {
        assert format != null;
        try {
            int value = Integer.parseInt(format);
            int row = value / 10 - 1;
            int column = value % 10 - 1;
            Coordinate coordinate = new Coordinate(row, column);
            if (!coordinate.isWithIn())
                return null;
            return coordinate;
        } catch (Exception ex) {
            return null;
        }
    }

    private boolean isWithIn() {
        return Coordinate.LOWER_LIMIT <= row && row <= Coordinate.UPPER_LIMIT && Coordinate.LOWER_LIMIT <= column
                && column <= Coordinate.UPPER_LIMIT;
    }

    private Coordinate substract(Coordinate coordinate) {
        return new Coordinate(this.row - coordinate.row, this.column - coordinate.column);
    }

    private Coordinate plus(Coordinate coordinate) {
        return new Coordinate(this.row + coordinate.row, this.column + coordinate.column);
    }

    public Direction getDirection(Coordinate coordinate) {
        assert coordinate != null;
        Coordinate substract = coordinate.substract(this);
        for (Direction direction : Direction.values()) 
            if (direction.isOnDirection(substract)) 
                return direction;
        return null;
    }

    public boolean isOnDiagonal(Coordinate coordinate) {
        return this.getDirection(coordinate) != null;
    }

    public int getDiagonalDistance(Coordinate coordinate) {
        assert this.isOnDiagonal(coordinate);
        return Math.abs(this.substract(coordinate).getRow());
    }

    Coordinate getBetweenDiagonalCoordinate(Coordinate coordinate) {
        assert this.getDiagonalDistance(coordinate) == 2;
        final Direction direction = this.getDirection(coordinate);
        return this.plus(direction.getDistanceCoordinate(1));
    }

    public List<Coordinate> getBetweenDiagonalCoordinates(Coordinate coordinate){
        assert this.isOnDiagonal(coordinate);
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        final Direction direction = this.getDirection(coordinate);
        Coordinate cursor = this.plus(direction.getDistanceCoordinate(1));
        while (!cursor.equals(coordinate)){
            coordinates.add(cursor);
            cursor = cursor.plus(direction.getDistanceCoordinate(1));
        }
        return coordinates;
    }

    List<Coordinate> getDiagonalCoordinates(int level) {
        List<Coordinate> diagonalCoordinates = new ArrayList<Coordinate>();
        for (Direction direction : Direction.values()) {
            Coordinate diagonalCoordinate = this.plus(direction.getDistanceCoordinate(level));
            if (diagonalCoordinate != null && diagonalCoordinate.isWithIn())
                diagonalCoordinates.add(diagonalCoordinate);
        }
        return diagonalCoordinates;
    }

    public boolean isLast() {
        return this.row == Coordinate.UPPER_LIMIT;
    }

    public boolean isFirst() {
        return this.row == Coordinate.LOWER_LIMIT;
    }

    public static int getDimension() {
        return Coordinate.DIMENSION;
    }

}