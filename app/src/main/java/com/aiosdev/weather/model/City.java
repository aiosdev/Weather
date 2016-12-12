package com.aiosdev.weather.model;

/**
 * Created by Administrator on 2016/12/11 0011.
 */

public class City {
    private String _id;
    private String name;
    private String country;
    private Coord coord;

    public class Coord {
        private double lon;
        private double lat;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "lon=" + lon +
                    ", lat=" + lat +
                    '}';
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "City{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", coord=" + coord +
                '}';
    }
}
