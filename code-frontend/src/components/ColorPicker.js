import React from 'react';
import Draggable from 'react-draggable';
import '../scss/ColorPicker.scss'; // Importando o arquivo Sass

const colors = [
  '#BAE2FF', '#B9FFDD', '#FFE8AC', '#FFCAB9', 
  '#F99494', '#9DD6FF', '#ECA1FF', '#DAFF8B', 
  '#FFA285', '#CDCDCD', '#979797', '#A99A7C',
];

const ColorPicker = ({ onSelectColor }) => {
  return (
    <Draggable>
      <div className="color-picker-container">
        <div className="color-picker-grid">
          {colors.map((color) => (
            <div
              key={color}
              onClick={() => onSelectColor(color)}
              className="color-circle"
              style={{ backgroundColor: color }}
            />
          ))}
        </div>
      </div>
    </Draggable>
  );
};

export default ColorPicker;
