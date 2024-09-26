import React, { Component } from 'react';
import TaskCard from './TaskCard';
import axios from 'axios';
import ColorFilter from './ColorFilter';
import TaskForm from './TaskForm';
import '../scss/TaskList.scss';

class TaskList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tasks: [],
      otherTasks: [],
      filteredColor: ''
    };
  }

  componentDidMount() {
    this.fetchTasks();
  }

  fetchTasks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/tasks');
      console.log('Tasks fetched:', response.data);

      this.setState({
        tasks: response.data.filter(task => task.favorite),
        otherTasks: response.data.filter(task => !task.favorite)
      });
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };

  handleColorFilter = (color) => {
    this.setState({ filteredColor: color });
  };

  handleDeleteTask = (id) => {
    this.setState(prevState => ({
      tasks: prevState.tasks.filter(task => task.id !== id),
      otherTasks: prevState.otherTasks.filter(task => task.id !== id)
    }));
  };

  handleTaskCreated = (newTask) => {
    if (newTask.favorite) {
      this.setState(prevState => ({
        tasks: [...prevState.tasks, newTask]
      }));
    } else {
      this.setState(prevState => ({
        otherTasks: [...prevState.otherTasks, newTask]
      }));
    }
  };

  handleColorChange = (id, newColor) => {
    this.setState(prevState => ({
      tasks: prevState.tasks.map(task =>
        task.id === id ? { ...task, color: newColor } : task
      ),
      otherTasks: prevState.otherTasks.map(task =>
        task.id === id ? { ...task, color: newColor } : task
      )
    }));
  };

  handleFavoriteChange = (id, newFavoriteStatus) => {
    this.setState(prevState => {
      const taskToUpdate = prevState.tasks.find(task => task.id === id) || 
                           prevState.otherTasks.find(task => task.id === id);

      if (!taskToUpdate) return prevState;

      // Atualiza o status de favorito
      taskToUpdate.favorite = newFavoriteStatus;

      
      const newTasks = prevState.tasks.filter(task => task.id !== id);
      const newOtherTasks = prevState.otherTasks.filter(task => task.id !== id);

      if (newFavoriteStatus) {
        newTasks.push(taskToUpdate); 
      } else {
        newOtherTasks.push(taskToUpdate); 
      }

      return {
        tasks: newTasks.sort((a, b) => b.favorite - a.favorite), 
        otherTasks: newOtherTasks.sort((a, b) => b.favorite - a.favorite) 
      };
    });
  };

  render() {
    const { searchTerm } = this.props;
    const { tasks, otherTasks, filteredColor } = this.state;

    const filteredFavoriteTasks = tasks.filter(task =>
      (filteredColor ? task.color === filteredColor : true) &&
      task.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const filteredOtherTasks = otherTasks.filter(task =>
      (filteredColor ? task.color === filteredColor : true) &&
      task.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
      <div className="task-list-container">
        <TaskForm onAddTask={this.handleTaskCreated} />

        <div className="color-filter-container">
          <ColorFilter onSelectColor={this.handleColorFilter} />
        </div>

        <h2 className="section-title">Favoritas</h2>
        <div className="task-grid">
          {filteredFavoriteTasks.map(task => (
            <TaskCard 
              key={task.id} 
              id={task.id} 
              title={task.title} 
              description={task.description} 
              initialFavorite={task.favorite} 
              initialColor={task.color} 
              onDelete={this.handleDeleteTask} 
              onColorChange={this.handleColorChange} 
              onFavoriteChange={this.handleFavoriteChange} 
            />
          ))}
        </div>

        <h2 className="section-title">Outras</h2>
        <div className="task-grid">
          {filteredOtherTasks.map(task => (
            <TaskCard 
              key={task.id} 
              id={task.id} 
              title={task.title} 
              description={task.description} 
              initialFavorite={task.favorite} 
              initialColor={task.color} 
              onDelete={this.handleDeleteTask} 
              onColorChange={this.handleColorChange} 
              onFavoriteChange={this.handleFavoriteChange} 
            />
          ))}
        </div>
      </div>
    );
  }
}

export default TaskList;
