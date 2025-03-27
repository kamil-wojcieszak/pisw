window.onload = () => {
  const form = document.getElementById("noteForm");
  const noteTitle = document.getElementById("noteTitle");
  const noteContent = document.getElementById("noteContent");
  const noteList = document.getElementById("noteList");

  const action = document.getElementById("action");

  const editNote = "Edit note";
  const createNote = "Create note";

  let currentNote = undefined;

  form.addEventListener("submit", (e) => {
    switch (urrentNote) {
      case undefined:
    }
    if (currentNote !== undefined) {
    } else {
    }
    localStorage.setItem(
      crypto.randomUUID(),
      JSON.stringify({
        id: crypto.randomUUID(),
        title: noteTitle.value,
        content: noteContent.value,
        date: Date.now(),
      })
    );
    render();
    // e.preventDefault();
  });

  const getNote = (e, note) => {
    currentNote = note;
    noteTitle.value = note.title;
    noteContent.value = note.content;
    render();
  };

  const addNoteToSidePanel = (note) => {
    li = document.createElement("li");
    li.innerHTML = note.title;
    li.onclick = (e) => getNote(e, note);
    noteList.appendChild(li);
  };

  const createNote(title,content) =>{
    return {id:x,title:title,content:content,date:Date.now()}
  }

  const addNoteToLocalStorage = (note) => {
    localStorage.setItem(
      note.id,
      JSON.stringify({
        id: note.id,
        title: note.title,
        content: note.content,
        date: note.date,
      })
    );
  };

  const getItemsFromLocalStorage = () => {
    const items = [];
    for (i = 0; i < localStorage.length; i++) {
      items.push(JSON.parse(localStorage.getItem(localStorage.key(i))));
    }
    return items.sort((a, b) => {
      return a.date < b.date;
    });
  };

  const currentAction = () => {
    if (currentNote !== undefined) {
      return editNote;
    }
    return createNote;
  };

  const render = () => {
    noteList.innerHTML = "";
    notes = getItemsFromLocalStorage();
    action.innerHTML = currentAction();
    notes.forEach((el) => addNoteToSidePanel(el));
  };

  render();
};
