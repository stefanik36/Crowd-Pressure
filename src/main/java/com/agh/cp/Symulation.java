package com.agh.cp;

public enum Symulation {
    SYM_P0_W0 {
        @Override
        public String toString() {
            return "SYM_P0_W0";
        }
    },
    SYM_P0_W1 {
        @Override
        public String toString() {
            return "SYM_P0_W1";
        }
    },
    SYM_P2_W0 {
        @Override
        public String toString() {
            return "SYM_P2_W0";
        }
    },
    SYM_P1_W1 {
        @Override
        public String toString() {
            return "SYM_P1_W1";
        }
    },
    SYM_P1_W2 {
        @Override
        public String toString() {
            return "SYM_P1_W2";
        }
    },
    SYM_ROOM {
        @Override
        public String toString() {
            return "SYM_ROOM";
        }
    },
    SYM_ROOM_OBSTACLE1 {
        @Override
        public String toString() {
            return "SYM_ROOM_OBSTACLE1";
        }
    },
    SYM_ROOM_PERP_WALL {
        @Override
        public String toString() {
            return "SYM_ROOM_PERP_WALL";
        }
    },
    SYM_PX_VS_PX_W0 {
        @Override
        public String toString() {
            return "SYM_PX_VS_PX_W0";
        }
    },
    SYM_PX_VS_P1_W0 {
        @Override
        public String toString() {
            return "SYM_PX_VS_P1_W0";
        }
    },
    SYM_PX_VS_PX_W2 {
        @Override
        public String toString() {
            return "SYM_PX_VS_PX_W2";
        }
    };

}
