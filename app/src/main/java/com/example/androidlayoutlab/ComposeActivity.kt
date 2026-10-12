package com.example.androidlayoutlab

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppRed = Color(0xFFC7242F)
private val PageGray = Color(0xFFF7F7F7)
private val TaskGray = Color(0xFFEDEDED)
private val MutedText = Color(0xFF6B6B6B)

private data class LearningTask(
    val id: Int,
    val title: String,
    val completed: Boolean = false
)

class ComposeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = AppRed,
                    onPrimary = Color.White,
                    surface = Color.White
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PageGray
                ) {
                    CourseTaskScreen()
                }
            }
        }
    }
}

@Composable
private fun CourseTaskScreen() {
    val tasks = mutableStateListOf(
        LearningTask(1, "学习 Column 和 Row", completed = true),
        LearningTask(2, "学习状态管理"),
        LearningTask(3, "完成 Compose 实验")
    )
    var input by rememberSaveable { mutableStateOf("") }
    var nextId by rememberSaveable { mutableStateOf(4) }
    val completedCount = tasks.count { it.completed }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Text(
            text = "课程学习任务",
            color = AppRed,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                placeholder = {
                    Text(
                        text = "请输入学习任务",
                        color = Color(0xFF8A8A8A),
                        fontSize = 13.sp
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val title = input.trim()
                        if (title.isNotEmpty()) {
                            tasks.add(LearningTask(nextId++, title))
                            input = ""
                        }
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppRed,
                    unfocusedBorderColor = Color(0xFFD2D2D2),
                    cursorColor = AppRed
                )
            )
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = {
                    val title = input.trim().ifEmpty { "复习 LazyColumn" }
                    tasks.add(LearningTask(nextId++, title))
                    input = ""
                },
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppRed),
                contentPadding = PaddingValues(horizontal = 18.dp)
            ) {
                Text("添加", fontSize = 14.sp)
            }
        }

        Spacer(Modifier.height(14.dp))
        Text(
            text = "已完成：$completedCount / ${tasks.size}",
            color = MutedText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = {
                        val index = tasks.indexOfFirst { it.id == task.id }
                        if (index >= 0) {
                            tasks[index] = tasks[index].copy(completed = !tasks[index].completed)
                        }
                    },
                    onDelete = {
                        tasks.removeAll { it.id == task.id }
                    }
                )
            }
        }
    }
}

@Composable
private fun TaskRow(
    task: LearningTask,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = TaskGray
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = AppRed,
                    checkmarkColor = Color.White,
                    uncheckedColor = Color(0xFF777777)
                )
            )
            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                color = if (task.completed) Color(0xFF777777) else Color(0xFF222222),
                fontSize = 14.sp,
                textDecoration = if (task.completed) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )
            TextButton(
                onClick = onDelete,
                colors = ButtonDefaults.textButtonColors(contentColor = AppRed)
            ) {
                Text("删除", fontSize = 12.sp)
            }
        }
    }
}
