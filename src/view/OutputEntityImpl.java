package view;

import view.EntityType;
import view.OutputEntity;

public class OutputEntityImpl implements OutputEntity {

        private final EntityType type;
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        public OutputEntityImpl(final EntityType type, final int x, final int y, final int width, final int height) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override
        public EntityType getType() {
            return type;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }
    }
