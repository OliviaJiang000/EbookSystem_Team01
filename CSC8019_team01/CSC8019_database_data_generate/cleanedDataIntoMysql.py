import pandas as pd
import mysql.connector

# 1. 读取 CSV 文件
df = pd.read_csv('Books_with_images_genres_cleaned.csv')
print(df.shape)
print(df.columns)
df=df[['title', 'author', 'genre', 'description', 'cover_image',
       'available_copies', 'loan_duration']]
print(df.head())
# 2. 连接到 MySQL
conn = mysql.connector.connect(
    host='localhost',
    user='admin',
    password='12345678',
    database='ebook_db'
)
cursor = conn.cursor()

# 3. 插入数据
for _, row in df.iterrows():
    sql = """
    INSERT INTO books (title, author, genre, description, cover_image, available_copies, loan_duration)
    VALUES (%s, %s, %s, %s, %s, %s, %s)
    """
    cursor.execute(sql, tuple(row))

conn.commit()
cursor.close()
conn.close()
