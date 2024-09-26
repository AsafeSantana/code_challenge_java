import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import vector2Icon from '../images/vector2.svg'; 
import favoriteIcon from '../images/favorite.svg'; 
import '../scss/TaskForm.scss'; 

const TaskForm = ({ onAddTask }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [favorite, setFavorite] = useState(false); 
  const formRef = useRef(null); 

  const handleSubmit = async () => {
    
    if (!title || !description) return; 

    try {
      const response = await axios.post('http://localhost:8080/api/tasks', {
        title,
        description,
        favorite, 
      });
      onAddTask(response.data); 

      
      setTitle('');
      setDescription('');
      setFavorite(false); 
    } catch (error) {
      console.error('Erro ao adicionar tarefa:', error);
    }
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (formRef.current && !formRef.current.contains(event.target)) {
        handleSubmit(); 
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [title, description, favorite]); 

  return (
    <div className="task-form-container">
      <div ref={formRef} className="task-form">
        <div className="task-form-header">
          <input
            type="text"
            placeholder="Título"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="task-form-title-input"
            required 
          />
          <img
            src={favorite ? favoriteIcon : vector2Icon} 
            alt="Favorito"
            className={`favorite-icon ${favorite ? 'active' : ''}`}
            onClick={() => setFavorite(!favorite)} 
          />
        </div>
        <hr className="divider" />
        <input
          type="text"
          placeholder="Criar nota..."
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="task-form-description-input"
          required 
        />
      </div>
    </div>
  );
};

export default TaskForm;
