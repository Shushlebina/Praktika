import tkinter as tk
from tkinter import ttk
import sqlite3

class ScheduleApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Расписание занятий")

        # Создаем соединение с базой данных
        self.conn = sqlite3.connect('расписание.db')
        self.cursor = self.conn.cursor()

        # Создаем таблицу для предметов
        self.cursor.execute('''
            CREATE TABLE IF NOT EXISTS предметы (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                название TEXT NOT NULL,
                день_недели TEXT NOT NULL,
                аудитория TEXT NOT NULL
            )
        ''')

        # Создаем таблицу для преподавателей
        self.cursor.execute('''
            CREATE TABLE IF NOT EXISTS преподаватели (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                ФИО TEXT NOT NULL,
                предмет_id INTEGER NOT NULL,
                количество_пар_в_неделю INTEGER NOT NULL,
                количество_студентов INTEGER NOT NULL,
                FOREIGN KEY (предмет_id) REFERENCES предметы (id)
            )
        ''')

        # Commit изменений
        self.conn.commit()

        # Вкладка для предметов
        self.tab_subjects = ttk.Frame(root)
        self.tab_subjects.pack(expand=1, fill="both")
        self.create_subjects_tab()

        # Вкладка для преподавателей
        self.tab_teachers = ttk.Frame(root)
        self.tab_teachers.pack(expand=1, fill="both")
        self.create_teachers_tab()

    def create_subjects_tab(self):
        frame_subjects = ttk.Frame(self.tab_subjects, padding="10")
        frame_subjects.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))

        ttk.Label(frame_subjects, text="Название предмета:").grid(row=0, column=0, sticky=tk.W)
        self.entry_subject_name = ttk.Entry(frame_subjects)
        self.entry_subject_name.grid(row=0, column=1, sticky=(tk.W, tk.E))

        ttk.Label(frame_subjects, text="День недели:").grid(row=1, column=0, sticky=tk.W)
        self.entry_subject_day = ttk.Entry(frame_subjects)
        self.entry_subject_day.grid(row=1, column=1, sticky=(tk.W, tk.E))

        ttk.Label(frame_subjects, text="Аудитория:").grid(row=2, column=0, sticky=tk.W)
        self.entry_subject_room = ttk.Entry(frame_subjects)
        self.entry_subject_room.grid(row=2, column=1, sticky=(tk.W, tk.E))

        ttk.Button(frame_subjects, text="Добавить предмет", command=self.add_subject).grid(row=3, column=0, columnspan=2, pady=10)

        # Создаем и отображаем Treeview для предметов
        self.subjects_tree = ttk.Treeview(frame_subjects, columns=("ID", "Название", "День недели", "Аудитория"), show="headings")
        self.subjects_tree.heading("ID", text="ID")
        self.subjects_tree.heading("Название", text="Название")
        self.subjects_tree.heading("День недели", text="День недели")
        self.subjects_tree.heading("Аудитория", text="Аудитория")
        self.subjects_tree.grid(row=4, column=0, columnspan=2, pady=10)

        # Обновляем Treeview
        self.update_subjects_tree()

    def create_teachers_tab(self):
        frame_teachers = ttk.Frame(self.tab_teachers, padding="10")
        frame_teachers.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))

        ttk.Label(frame_teachers, text="ФИО преподавателя:").grid(row=0, column=0, sticky=tk.W)
        self.entry_teacher_name = ttk.Entry(frame_teachers)
        self.entry_teacher_name.grid(row=0, column=1, sticky=(tk.W, tk.E))

        ttk.Label(frame_teachers, text="Название предмета:").grid(row=1, column=0, sticky=tk.W)
        self.entry_teacher_subject = ttk.Entry(frame_teachers)
        self.entry_teacher_subject.grid(row=1, column=1, sticky=(tk.W, tk.E))

        ttk.Label(frame_teachers, text="Количество пар в неделю:").grid(row=2, column=0, sticky=tk.W)
        self.entry_teacher_lectures = ttk.Entry(frame_teachers)
        self.entry_teacher_lectures.grid(row=2, column=1, sticky=(tk.W, tk.E))

        ttk.Label(frame_teachers, text="Количество студентов:").grid(row=3, column=0, sticky=tk.W)
        self.entry_teacher_students = ttk.Entry(frame_teachers)
        self.entry_teacher_students.grid(row=3, column=1, sticky=(tk.W, tk.E))

        ttk.Button(frame_teachers, text="Добавить преподавателя", command=self.add_teacher).grid(row=4, column=0, columnspan=2, pady=10)

        # Создаем и отображаем Treeview для преподавателей
        self.teachers_tree = ttk.Treeview(frame_teachers, columns=("ID", "ФИО", "Название предмета", "Количество пар в неделю", "Количество студентов"), show="headings")
        self.teachers_tree.heading("ID", text="ID")
        self.teachers_tree.heading("ФИО", text="ФИО")
        self.teachers_tree.heading("Название предмета", text="Название предмета")
        self.teachers_tree.heading("Количество пар в неделю", text="Количество пар в неделю")
        self.teachers_tree.heading("Количество студентов", text="Количество студентов")
        self.teachers_tree.grid(row=5, column=0, columnspan=2, pady=10)

        # Обновляем Treeview
        self.update_teachers_tree()

    def add_subject(self):
        name = self.entry_subject_name.get()
        day = self.entry_subject_day.get()
        room = self.entry_subject_room.get()

        if name and day and room:
            self.cursor.execute('INSERT INTO предметы (название, день_недели, аудитория) VALUES (?, ?, ?)', (name, day, room))
            self.conn.commit()
            self.update_subjects_tree()

    def add_teacher(self):
        name = self.entry_teacher_name.get()
        subject = self.entry_teacher_subject.get()
        lectures = self.entry_teacher_lectures.get()
        students = self.entry_teacher_students.get()

        if name and subject and lectures and students:
            # Получаем id предмета по его названию
            self.cursor.execute('SELECT id FROM предметы WHERE название = ?', (subject.strip(),))
            subject_id = self.cursor.fetchone()

            if subject_id:
                # Вставляем преподавателя в таблицу
                self.cursor.execute('INSERT INTO преподаватели (ФИО, предмет_id, количество_пар_в_неделю, количество_студентов) VALUES (?, ?, ?, ?)',
                                    (name, subject_id[0], lectures, students))
                self.conn.commit()
                self.update_teachers_tree()
            else:
                print(f"Error adding teacher: Subject '{subject}' not found.")
        else:
            print("Error adding teacher: Please fill in all fields.")

    def update_subjects_tree(self):
        # Очищаем Treeview
        for row in self.subjects_tree.get_children():
            self.subjects_tree.delete(row)

        # Выбираем данные из базы данных
        self.cursor.execute('SELECT * FROM предметы')
        subjects = self.cursor.fetchall()

        # Вставляем данные в Treeview
        for subject in subjects:
            self.subjects_tree.insert("", "end", values=subject)

    def update_teachers_tree(self):
        # Очищаем Treeview
        for row in self.teachers_tree.get_children():
            self.teachers_tree.delete(row)

        # Выбираем данные из базы данных
        self.cursor.execute('SELECT преподаватели.id, преподаватели.ФИО, предметы.название, преподаватели.количество_пар_в_неделю, преподаватели.количество_студентов FROM преподаватели JOIN предметы ON преподаватели.предмет_id = предметы.id')
        teachers = self.cursor.fetchall()

        # Вставляем данные в Treeview
        for teacher in teachers:
            self.teachers_tree.insert("", "end", values=teacher)

if __name__ == "__main__":
    root = tk.Tk()
    app = ScheduleApp(root)
    root.mainloop()


