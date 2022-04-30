package org.cambural21.solidity.wrapper;

public enum Feature {

    IERC20Backed("sp"),
    IERC20Paused("sp"),

    IERC2771("std"),
    IERC2612("std"),
    IERC1820("std"),
    IERC1046("std"),
    UNISWAP("std"),
    IERC173("std"),
    IERC165("std"),

    IERC20("base");

    private final String type;

    Feature(String type){
        this.type = type;
    }

    public boolean isBase(){
        return type.equals("base");
    }

    public boolean isSpecial(){
        return type.equals("sp");
    }

    public boolean isStandard(){
        return type.equals("std");
    }

    public static Feature parse(String value){
        Feature found = null;
        if(value != null && !value.isEmpty()){
            for (Feature feature: Feature.values()) {
                if(String.valueOf(feature).equalsIgnoreCase(value)){
                    found = feature;
                    break;
                }
            }
        }
        return found;
    }

}