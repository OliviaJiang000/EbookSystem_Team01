import pandas as pd

# 加载CSV文件
input_file = 'books_with_images_genres_Google.csv'
output_file = 'Books_with_images_genres_cleaned.csv'

# 读取CSV数据
df = pd.read_csv(input_file)

# 1. 去除重复记录
df.drop_duplicates(subset=['title', 'author'], keep='first', inplace=True)

# 2. 统一作者格式 (如果有多个作者，用逗号隔开并去掉空格)
df['author'] = df['author'].apply(lambda x: ', '.join([author.strip() for author in str(x).split('/')]))

# 3. 清洗描述字段
# 将缺失的描述填充为 "暂无简介"
df['description'] = df['description'].fillna("This book has no description available yet. ")

# 4. 格式化封面图链接 (检查是否存在有效的封面链接)
df['cover_image'] = df['cover_image'].apply(lambda x: x.strip() if isinstance(x, str) and x.startswith('http') else 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQd3L0ilsow7EvTdnwss2XttKd00LuUWtAL_g&s')

# 5. 增加 `series` 和 `volume` 字段
df['series'] = df['title'].apply(lambda x: 'Harry Potter' if 'Harry Potter' in x else 'N/A')
df['volume'] = df['title'].apply(lambda x: ''.join([i for i in x if i.isdigit()]) if any(char.isdigit() for char in x) else 'N/A')

# 6. 处理空值: 如果有其他列的空值，我们填充默认值
df['genre'] = df['genre'].fillna('Uncategorized')
df['available_copies'] = df['available_copies'].fillna(10)  # 默认 10 本



# 7. 清洗数据: 去除字段中的额外空格
df.columns = df.columns.str.strip()

# 8. 输出清洗后的数据到新的CSV文件
df.to_csv(output_file, index=False)

print(f"数据清洗完成，已保存至 {output_file}")
